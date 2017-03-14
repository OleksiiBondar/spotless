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
package com.diffplug.gradle.spotless;

import java.io.IOException;
import java.lang.reflect.Method;

import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Before;
import org.junit.Test;

import com.diffplug.common.testing.NullPointerTester;
import com.diffplug.spotless.ResourceHarness;

public class FormatExtensionTest extends ResourceHarness {
	private Method licenseHeaderFileInstanceMethod;
	private FormatExtension formatExtensionTestInstance;

	@Before
	public void setUp() throws Exception {
		licenseHeaderFileInstanceMethod = FormatExtension.class.getMethod("licenseHeaderFile", Object.class, String.class);
		formatExtensionTestInstance = new FormatExtension(new SpotlessExtension(ProjectBuilder.builder().withProjectDir(rootFolder()).build()));
	}

	@Test
	public void testNulls() throws NoSuchMethodException, IOException {
		NullPointerTester tester = new NullPointerTester();
		tester.testAllPublicConstructors(FormatExtension.class);
		tester.testAllPublicStaticMethods(FormatExtension.class);

		new NullPointerTester().ignore(licenseHeaderFileInstanceMethod).testAllPublicInstanceMethods(formatExtensionTestInstance);

		new NullPointerTester().setDefault(Object.class, "non-empty-string").testMethod(formatExtensionTestInstance, licenseHeaderFileInstanceMethod);
	}
}
