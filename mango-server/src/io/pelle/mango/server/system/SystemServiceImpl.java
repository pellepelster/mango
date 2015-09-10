package io.pelle.mango.server.system;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.pelle.mango.client.system.ISystemService;
import io.pelle.mango.client.system.Systeminformation;

@RestController
@RequestMapping("systemservice")
public class SystemServiceImpl implements ISystemService {

	private static Logger LOG = Logger.getLogger(SystemServiceImpl.class);

	private static String MESSAGES_FILE = "classpath*:i18n/*_dictionary_messages%s.properties";

	@Override
	public Systeminformation getSystemInformation() {

		Systeminformation result = new Systeminformation();

		try {
			result.setHostName(InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			LOG.error(e);
		}

		return result;
	}

	@RequestMapping(value = "getdictionaryi18nscript", produces = "application/javascript", method = RequestMethod.GET)
	public void getDictionaryI18NScript(String variableName, HttpServletResponse httpServletResponse) throws IOException {

		Locale locale = LocaleContextHolder.getLocale();

		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

		Resource[] resources;

		try {

			if (locale.getCountry() != null) {
				resources = resolver.getResources(String.format(MESSAGES_FILE, "_" + locale.getLanguage().toLowerCase() + "_" + locale.getCountry().toLowerCase()));
			} else {
				resources = resolver.getResources(String.format(MESSAGES_FILE, "_" + locale.getLanguage().toLowerCase()));
			}

			if (resources.length == 0) {
				resources = resolver.getResources(String.format(MESSAGES_FILE, ""));
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		StringBuffer sb = new StringBuffer();

		sb.append("var " + variableName + " = { ");

		for (Resource resource : resources) {
			Properties properties = new Properties();
			try {
				properties.load(resource.getInputStream());

				for (Entry<Object, Object> e : properties.entrySet()) {

					sb.append(String.format("%s: \"%s\", ", e.getKey(), e.getValue()));
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		sb.append(" };");

		httpServletResponse.setContentType("application/javascript");
		httpServletResponse.getWriter().print(sb.toString());
		httpServletResponse.getWriter().close();
	}

}
