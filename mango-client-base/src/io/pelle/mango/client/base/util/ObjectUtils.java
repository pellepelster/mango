package io.pelle.mango.client.base.util;

import javax.annotation.Nullable;

import com.google.common.base.Objects;

public class ObjectUtils
{

	public static <T> T firstNonNull(@Nullable T first, @Nullable T second, @Nullable T third)
	{
		return Objects.firstNonNull(first, Objects.firstNonNull(second, third));
	}

	public static <T> T firstNonNull(@Nullable T first, @Nullable T second)
	{
		return Objects.firstNonNull(first, second);
	}

}
