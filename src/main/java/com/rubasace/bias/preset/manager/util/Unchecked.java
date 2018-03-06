package com.rubasace.bias.preset.manager.util;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Unchecked {

	// public static final Consumer<Exception> EXCEPTION_TO_RUNTIME_EXCEPTION = e -> {
	//
	// if(e instanceof RuntimeException) {
	// throw (RuntimeException) e;
	// }
	//
	// throw new RuntimeException(e);
	//
	// };
	//
	// public static final Consumer<Exception> RETHROW_ALL = ExceptionUtils::rethrow;

	public static Runnable runnable(CheckedRunnable runnable) {
		return () -> {
			try {
				runnable.run();
			} catch(Exception e) {
				ExceptionUtils.rethrow(e);
			}
		};
	}

	public static Runnable runnable(CheckedRunnable runnable, Function<Exception, ? extends Exception> exceptionMapper) {
		return () -> {
			try {
				runnable.run();
			} catch(Exception e) {
				ExceptionUtils.rethrow(exceptionMapper.apply(e));
			}
		};
	}

	public static <T> Consumer<T> consumer(CheckedConsumer<T> runnable) {
		return v -> {
			try {
				runnable.accept(v);
			} catch(Exception e) {
				ExceptionUtils.rethrow(e);
			}
		};
	}

	public static <T> Consumer<T> consumer(CheckedConsumer<T> runnable, Function<Exception, ? extends Exception> exceptionMapper) {
		return v -> {
			try {
				runnable.accept(v);
			} catch(Exception e) {
				ExceptionUtils.rethrow(exceptionMapper.apply(e));
			}
		};
	}

	public static <T, U> Function<T, U> function(CheckedFunction<T, U> runnable) {
		return v -> {
			try {
				return runnable.apply(v);
			} catch(Exception e) {
				return ExceptionUtils.rethrow(e);
			}
		};
	}

	public static <T, U> Function<T, U> function(CheckedFunction<T, U> runnable, Function<Exception, ? extends Exception> exceptionMapper) {
		return v -> {
			try {
				return runnable.apply(v);
			} catch(Exception e) {
				return ExceptionUtils.rethrow(exceptionMapper.apply(e));
			}
		};
	}

	public static <T> Supplier<T> supplier(CheckedSupplier<T> supplier) {
		return () -> {
			try {
				return supplier.get();
			} catch(Exception e) {
				return ExceptionUtils.rethrow(e);
			}
		};
	}

	public static <T> Supplier<T> supplier(CheckedSupplier<T> supplier, Function<Exception, ? extends Exception> exceptionMapper) {
		return () -> {
			try {
				return supplier.get();
			} catch(Exception e) {
				return ExceptionUtils.rethrow(exceptionMapper.apply(e));
			}
		};
	}

	public static interface CheckedRunnable {
		void run() throws Exception;
	}

	public static interface CheckedConsumer<T> {
		void accept(T t) throws Exception;
	}

	public static interface CheckedFunction<T, U> {
		U apply(T t) throws Exception;
	}

	public static interface CheckedSupplier<T> {
		T get() throws Exception;
	}

}
