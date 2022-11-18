package mc.server.survival.libraries;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

public class TimeLib
{
	private TimeLib() {}

	static TimeLib instance = new TimeLib();

	public static TimeLib getInstance() {return instance;}

	public UnitUtil getUnitUtil(final @NotNull String unit) { return new UnitUtil(unit); }

	public static String hour() 
	{
		final Date now = new Date();
		final SimpleDateFormat format = new SimpleDateFormat("HH");
		
		return format.format(now) + "";
	}
	
	public static String minute() 
	{
		final Date now = new Date();
		final SimpleDateFormat format = new SimpleDateFormat("mm");
		
		return format.format(now) + "";
	}

	public static String date()
	{
		final LocalDateTime date = LocalDateTime.now();

		return date.getYear() + "-" + date.getMonthValue() + "-" + date.getDayOfMonth() + " / " + date.getHour() + "-" + date.getMinute() + "-" + date.getSecond();
	}

	public static int getDifferenceInHours(String lastDate)
	{
		final String minutes = lastDate.substring(lastDate.length() - 5, lastDate.length() - 3);
		final int minute = Integer.parseInt(minutes);
		final String hours = lastDate.substring(lastDate.length() - 8, lastDate.length() - 6);
		final int hour = Integer.parseInt(hours);
		final String days = lastDate.substring(lastDate.length() - 11, lastDate.length() - 9);
		final int day = Integer.parseInt(days);
		final String months = lastDate.substring(lastDate.length() - 14, lastDate.length() - 12);
		final int month = Integer.parseInt(months);
		final String years = lastDate.substring(lastDate.length() - 19, lastDate.length() - 15);
		final int year = Integer.parseInt(years);
		final long time = Duration.between(LocalDateTime.of(year, month, day, hour, minute, 0), LocalDateTime.now()).toHours();
		final String str = Long.toString(time);

		return Integer.parseInt(str);
	}

	public static int getDifferenceInMinutes(String lastDate)
	{
		final String minutes = lastDate.substring(lastDate.length() - 5, lastDate.length() - 3);
		final int minute = Integer.parseInt(minutes);
		final String hours = lastDate.substring(lastDate.length() - 8, lastDate.length() - 6);
		final int hour = Integer.parseInt(hours);
		final String days = lastDate.substring(lastDate.length() - 11, lastDate.length() - 9);
		final int day = Integer.parseInt(days);
		final String months = lastDate.substring(lastDate.length() - 14, lastDate.length() - 12);
		final int month = Integer.parseInt(months);
		final String years = lastDate.substring(lastDate.length() - 19, lastDate.length() - 15);
		final int year = Integer.parseInt(years);
		final long time = Duration.between(LocalDateTime.of(year, month, day, hour, minute, 0), LocalDateTime.now()).toMinutes();
		final String str = Long.toString(time);

		return Integer.parseInt(str);
	}

	public static int getDifferenceInSeconds(String lastDate)
	{
		final String seconds = lastDate.substring(lastDate.length() - 2);
		final int second = Integer.parseInt(seconds);
		final String minutes = lastDate.substring(lastDate.length() - 5, lastDate.length() - 3);
		final int minute = Integer.parseInt(minutes);
		final String hours = lastDate.substring(lastDate.length() - 8, lastDate.length() - 6);
		final int hour = Integer.parseInt(hours);
		final String days = lastDate.substring(lastDate.length() - 11, lastDate.length() - 9);
		final int day = Integer.parseInt(days);
		final String months = lastDate.substring(lastDate.length() - 14, lastDate.length() - 12);
		final int month = Integer.parseInt(months);
		final String years = lastDate.substring(lastDate.length() - 19, lastDate.length() - 15);
		final int year = Integer.parseInt(years);
		final long time = Duration.between(LocalDateTime.of(year, month, day, hour, minute, second), LocalDateTime.now()).toMillis() / 1000;
		final String str = Long.toString(time);

		return Integer.parseInt(str);
	}

	public class UnitUtil
	{
		private final @NotNull String unit;

		public UnitUtil(@NotNull String unit)
		{
			this.unit = unit;
		}

		public boolean isCorrectUnit()
		{
			final String substring = unit.substring(unit.length() - 1);
			final String[] units = {"s", "m", "h"};

			for (String unit : units)
				if (substring.equalsIgnoreCase(unit))
					return true;

			return false;
		}

		public int convert()
		{
			final String substring = unit.substring(unit.length() - 1);

			if (substring.equalsIgnoreCase("s"))
				return 1;

			if (substring.equalsIgnoreCase("m"))
				return 60;

			if (substring.equalsIgnoreCase("h"))
				return 3600;

			return -1;
		}

		public boolean isCorrectTime()
		{
			if (unit.length() != 2 && unit.length() != 3) return false;

			final String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
			final String substring = unit.substring(unit.length() - 2, unit.length() - 1);

			if (unit.length() == 2)
			{
				for (String number : numbers)
					if (substring.equalsIgnoreCase(number))
						return true;

				return false;
			}

			for (String number : numbers)
				if (substring.equalsIgnoreCase(number))
					for (String number2 : numbers)
						if (unit.substring(0, unit.length() - 2).equalsIgnoreCase(number2))
							return true;

			return false;
		}

		public int catchTime()
		{
			return Integer.parseInt(unit.substring(0, unit.length() - 1)) * convert();
		}
	}
}