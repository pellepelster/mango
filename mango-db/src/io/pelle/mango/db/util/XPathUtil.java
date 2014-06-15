package io.pelle.mango.db.util;
public final class XPathUtil
{

	public static String combine(String path1, String path2)
	{

		if (path1 == null && path2 != null)
		{
			return path2;
		}

		if (path1 != null && path2 == null)
		{
			return path1;
		}

		if (path1 == null && path2 == null)
		{
			return "";
		}

		if ("".equals(path1) || "".equals(path2))
		{
			return path1 + path2;
		}

		if (isIndex(path2))
		{
			if (path1.endsWith("/"))
			{

				if (path1.length() > 1)
				{
					return path1.substring(0, path1.length() - 2) + path2;
				}
				else
				{
					throw new RuntimeException("can not concatenate '" + path1 + "' and '" + path2 + "'");
				}

			}
			else
			{
				return path1 + path2;
			}
		}
		else
		{
			if (path1.endsWith("/") || path2.startsWith("/"))
			{
				return path1 + path2;
			}
			else
			{
				return path1 + "/" + path2;
			}
		}

	}

	/**
	 * Combines three xpaths
	 * 
	 * @param path1
	 * @param path2
	 * @param path3
	 * @return
	 */
	public static String combine(String path1, String path2, String path3)
	{
		return combine(combine(path1, path2), path3);
	}

	public static boolean containsList(String attributePath)
	{
		return attributePath.contains("[");
	}

	public static String getFirstPath(String attributePath)
	{

		if (attributePath == null)
		{
			return attributePath;
		}
		else if (!attributePath.contains("/"))
		{
			return attributePath;
		}
		else
		{
			return attributePath.substring(0, attributePath.indexOf("/"));
		}
	}

	public static String getLastPath(String path)
	{
		String strippedPath = removeLastPath(path);

		if (strippedPath == null)
		{
			return null;
		}

		if (strippedPath.equals(path))
		{
			return null;
		}
		else
		{
			String result = path.substring(strippedPath.length() + 1);

			if (result.endsWith("/"))
			{
				result = result.substring(0, result.length() - 1);
			}

			return result;
		}
	}

	public static int getPathLevels(String path)
	{
		int result = 0;

		for (int i = 0; i < path.length(); i++)
		{
			if (path.charAt(i) == '/')
			{
				result++;
			}
		}
		return result;
	}

	public static String getPathToFirstList(String attributePath)
	{
		if (attributePath != null && attributePath.contains("[")) { //$NON-NLS-1$
			return attributePath.substring(0, attributePath.indexOf("["));
		}
		else
		{
			return null;
		}
	}

	private static boolean isIndex(String indexString)
	{
		return indexString.startsWith("[") && indexString.endsWith("]");
	}

	public static boolean isSingleLevel(String path)
	{
		return !path.contains("/");
	}

	public static String removeLastPath(String path)
	{
		String result = path;

		if (result == null)
		{
			return result;
		}

		if (!result.isEmpty() && result.charAt(result.length() - 1) == '/')
		{
			result = result.substring(0, result.length() - 2);
		}

		if (!result.contains("/"))
		{
			return result;
		}
		else
		{
			return result.substring(0, result.lastIndexOf("/"));
		}

	}

	public static String addListIndentifier(String attributePath, long listIdentifier)
	{
		if (attributePath == null)
		{
			return attributePath;
		}
		else
		{
			return attributePath + "[" + listIdentifier + "]";
		}
	}

	public static String removeListIdentifiers(String attributePath)
	{
		if (attributePath == null)
		{
			return attributePath;
		}
		else
		{
			return attributePath.replaceAll("\\[.*?\\]", "");
		}
	}

	private XPathUtil()
	{
	}

}
