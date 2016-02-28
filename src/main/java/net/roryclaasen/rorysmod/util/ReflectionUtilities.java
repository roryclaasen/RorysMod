/*
Copyright 2016 Rory Claasen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package net.roryclaasen.rorysmod.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionUtilities {

	@SuppressWarnings("rawtypes")
	public static Field getField(String methodName, String obfname, Class classy) {
		for (Field i : classy.getDeclaredFields()) {
			if (i.getName() == methodName) {
				RMLog.info("Found Field " + methodName + " as " + methodName);
				return i;
			} else if (i.getName() == obfname) {
				RMLog.info("Found Field " + methodName + " as " + obfname);
				return i;
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public static Method getMethod(String methodName, String obfname, Class classy, Class... parameters) {
		for (Method i : classy.getDeclaredMethods()) {
			if (i.getName() == methodName) {
				RMLog.info("Found Method " + methodName + " as " + methodName);
				return i;
			} else if (i.getName() == obfname) {
				RMLog.info("Found Method " + methodName + " as " + obfname);
				return i;
			}
		}
		return null;
	}
}