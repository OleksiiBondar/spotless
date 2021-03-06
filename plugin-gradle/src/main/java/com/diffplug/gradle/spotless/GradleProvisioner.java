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

import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;

import com.diffplug.spotless.Provisioner;

/** Gradle integration for Provisioner. */
public class GradleProvisioner {
	private GradleProvisioner() {}

	public static Provisioner fromProject(Project project) {
		return mavenCoords -> {
			Dependency[] deps = mavenCoords.stream()
					.map(project.getDependencies()::create)
					.toArray(Dependency[]::new);
			Configuration config = project.getConfigurations().detachedConfiguration(deps);
			config.setDescription(mavenCoords.toString());
			return config.resolve();
		};
	}
}
