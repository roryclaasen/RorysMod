/*
 * Copyright 2017 Rory Claasen
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.roryclaasen.rorysmod.core;

import me.roryclaasen.githubcheck.VersionCheck;
import me.roryclaasen.githubcheck.data.Release;
import me.roryclaasen.rorysmod.util.RMLog;

public class Version implements Runnable {
	private VersionCheck check;

	private Release release;

	private boolean latest;

	public boolean haveWarnedVersionOutOfDate = false;

	public Version(String version) {
		check = new VersionCheck("GOGO98901", "RorysMod", version);
	}

	@Override
	public void run() {
		latest = check.isLatestRelease();
		if (latest) {
			RMLog.info("This is the latest release");
		} else {
			RMLog.info("This is not the latest release");
		}
		try {
			release = check.getLatestVersion(false);
			RMLog.info(release.getName() + " - " + release.getUrl());
		} catch (Exception e) {}
	}

	public Release getLatest() {
		return release;
	}

	public boolean isLatestVersion() {
		return latest;
	}
}
