/*
 * Copyright 2016 DiffPlug
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.diffplug.spotless.extra.java;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.diffplug.common.testing.NullPointerTester;
import com.diffplug.spotless.FormatterStep;
import com.diffplug.spotless.LazyForwardingEquality;
import com.diffplug.spotless.ResourceHarness;
import com.diffplug.spotless.SerializableEqualityTester;
import com.diffplug.spotless.StepHarness;
import com.diffplug.spotless.TestProvisioner;

public class EclipseFormatterStepTest extends ResourceHarness {
	@Test
	public void loadPropertiesSettings() throws Throwable {
		File eclipseFormatFile = createTestFile("java/eclipse/format/formatter.properties");
		StepHarness.forStep(EclipseFormatterStep.create(eclipseFormatFile, TestProvisioner.mavenCentral()))
				.testResource("java/eclipse/format/JavaCodeUnformatted.test", "java/eclipse/format/JavaCodeFormatted.test");
	}

	@Test
	public void loadXmlSettings() throws Throwable {
		File eclipseFormatFile = createTestFile("java/eclipse/format/formatter.xml");
		StepHarness.forStep(EclipseFormatterStep.create(eclipseFormatFile, TestProvisioner.mavenCentral()))
				.testResource("java/eclipse/format/JavaCodeUnformatted.test", "java/eclipse/format/JavaCodeFormatted.test");
	}

	@Test
	public void longLiteralProblem() throws Throwable {
		String folder = "java/eclipse/format/long_literals/";
		File eclipseFormatFile = createTestFile(folder + "spotless.eclipseformat.xml");
		StepHarness.forStep(EclipseFormatterStep.create(eclipseFormatFile, TestProvisioner.mavenCentral()))
				.testResourceUnaffected(folder + "Example1.test")
				.testResourceUnaffected(folder + "Example2.test");
	}

	@Test
	public void equality() throws IOException {
		File xmlFile = createTestFile("java/eclipse/format/formatter.xml");
		File propFile = createTestFile("java/eclipse/format/formatter.properties");
		new SerializableEqualityTester() {
			File settingsFile;

			@Override
			protected void setupTest(API api) {
				settingsFile = xmlFile;
				api.areDifferentThan();

				settingsFile = propFile;
				api.areDifferentThan();
			}

			@Override
			protected FormatterStep create() {
				return EclipseFormatterStep.create(settingsFile, TestProvisioner.mavenCentral());
			}
		}.testEquals();
	}

	@Test
	public void testNulls() throws Exception {
		NullPointerTester tester = new NullPointerTester();
		tester.testAllPublicConstructors(EclipseFormatterStep.class);
		tester.testAllPublicStaticMethods(EclipseFormatterStep.class);
		tester
				.ignore(LazyForwardingEquality.class.getMethod("equals", Object.class))
				.testAllPublicInstanceMethods(EclipseFormatterStep.create(createTestFile("java/eclipse/format/formatter.xml"), TestProvisioner.mavenCentral()));
	}
}
