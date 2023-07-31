package dev.minemesh.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static dev.minemesh.controller.util.StringUtil.generateIdString;

@SpringBootApplication
public class ControllerApplication {

	private static short counter = 0;

	public static void main(String[] args) throws InterruptedException {
		System.out.println(Integer.MAX_VALUE);
//		SpringApplication.run(ControllerApplication.class, args);
	}
}
