package io.pelle.mango.client.base.util;

import java.util.Map;

public class MessageFormat
{
	public static String format(String string, Map<String, Object> tokens)
	{
		for (Map.Entry<String, Object> token : tokens.entrySet())
		{
			String delimiter = "{" + token.getKey() + "}";

			while (string.contains(delimiter))
			{
				string = string.replace(delimiter, String.valueOf(token.getValue()));
			}
		}

		return string;
	}

	public static String format(String string, Object... tokens)
	{
		int i = 0;
		while (i < tokens.length)
		{
			String delimiter = "{" + i + "}";

			while (string.contains(delimiter))
			{
				string = string.replace(delimiter, String.valueOf(tokens[i]));
			}

			i++;
		}

		return string;
	}
}
