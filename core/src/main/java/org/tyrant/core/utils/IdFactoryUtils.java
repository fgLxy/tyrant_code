package org.tyrant.core.utils;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;



public class IdFactoryUtils {
	private static Cache<Long, AtomicInteger> MILLION_ADDER_CACHE = CacheBuilder.newBuilder().maximumSize(10000)
			.expireAfterWrite(1, TimeUnit.SECONDS).build();
	private static byte[] MAC_BYTES = ServerInfoUtils.getMacId();
	private static int PID = ServerInfoUtils.getPid();
	
	private final static String DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final static BigInteger BASE = BigInteger.valueOf(DIGITS.length());
	
	/**
	 * 生成唯一id
	 * @return
	 */
	
	public static String generateId() {
		long timestamp = System.currentTimeMillis();
        long time = timestamp - DateUtils.currentDateTimestamp();
        int seq = getMillionSequence(timestamp);

        ByteBuffer buf = ByteBuffer.allocate(15);
        buf.putInt((int) time);
        buf.put(MAC_BYTES);
        buf.put((byte) (seq & 0xFF));
        buf.put((byte) (PID >> 8));
        buf.put((byte) (PID & 0xFF));
        long tId = Thread.currentThread().getId();
        buf.put((byte) (tId >> 8));
        buf.put((byte) (tId & 0xFF));
        BigInteger bi = new BigInteger(buf.array());
        return DateUtils.currentDateTime("yyyyMMdd") + encode(bi);
    }
	
	private static int getMillionSequence(long timestamp) {
		AtomicInteger adder = getMillionAdder(timestamp);
		return adder.incrementAndGet();
	}
	private static AtomicInteger getMillionAdder(long timestamp) {
		AtomicInteger adder = MILLION_ADDER_CACHE.getIfPresent(timestamp);
		if (adder == null) {
			synchronized(IdFactoryUtils.class) {
				adder = MILLION_ADDER_CACHE.getIfPresent(timestamp);
				if (adder == null) {
					adder = new AtomicInteger(0);
					MILLION_ADDER_CACHE.put(timestamp, adder);
				}
			}
		}
		return adder;
	}

	private static String encode(BigInteger number) {
        // number < 0
        if (number.compareTo(BigInteger.ZERO) == -1) {
            throw new IllegalArgumentException("number must not be negative");
        }

        StringBuilder result = new StringBuilder();
        // number > 0
        while (number.compareTo(BigInteger.ZERO) == 1) {
            BigInteger[] divmod = number.divideAndRemainder(BASE);
            number = divmod[0];
            int digit = divmod[1].intValue();
            result.insert(0, DIGITS.charAt(digit));
        }

        return (result.length() == 0) ? DIGITS.substring(0, 1) : result.toString();
    }
}
