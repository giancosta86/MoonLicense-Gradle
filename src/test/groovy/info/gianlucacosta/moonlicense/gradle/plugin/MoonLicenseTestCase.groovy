/*§
  ===========================================================================
  MoonLicense - Gradle
  ===========================================================================
  Copyright (C) 2015 Gianluca Costa
  ===========================================================================
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  ===========================================================================
*/

package info.gianlucacosta.moonlicense.gradle.plugin

import info.gianlucacosta.moonlicense.gradle.dsl.MoonLicenseSettings
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.rules.TemporaryFolder


abstract class MoonLicenseTestCase extends GroovyTestCase {
    private TemporaryFolder tempFolder = new TemporaryFolder()
    private Project project

    @Override
    void setUp() {
        super.setUp()

        tempFolder.create()

        project = ProjectBuilder.builder()
                .withName("MoonTest")
                .withProjectDir(tempFolder.getRoot())
                .build()

        project.plugins.apply("maven")
        project.plugins.apply("info.gianlucacosta.moonlicense")
    }


    @Override
    void tearDown() {
        tempFolder.delete()

        super.tearDown();
    }


    protected Project getProject() {
        return project
    }

    protected MoonLicenseSettings getSettings() {
        return project.moonLicense
    }
}
