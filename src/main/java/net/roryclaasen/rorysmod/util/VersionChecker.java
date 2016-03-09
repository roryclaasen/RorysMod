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

import net.roryclaasen.githubcheck.VersionCheck;
import net.roryclaasen.githubcheck.data.Release;

public class VersionChecker implements Runnable {

	private VersionCheck version;

	private boolean latest;

	public static boolean haveWarnedVersionOutOfDate = false;

	public VersionChecker(String stringVersion) {
		version = new VersionCheck("GOGO98901", "RorysMod", stringVersion);
	}

	@Override
	public void run() {
		latest = version.isLatestRelease();
		if (latest) RMLog.info("This is the latest release");
		else {
			RMLog.info("This is not the latest release");
			try {
				Release release = version.getLatestVersion(false);
				RMLog.info(release.getName() + " - " + release.getUrl());
			} catch (Exception e) {
			}
		}
	}

	public boolean isLatestVersion() {
		return latest;
	}
}
