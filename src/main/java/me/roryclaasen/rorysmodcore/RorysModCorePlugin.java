/*
 * Copyright 2016-2017 Rory Claasen
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
package me.roryclaasen.rorysmodcore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.DependsOn;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.SortingIndex;
import me.roryclaasen.rorysmodcore.asm.ASMHelper;
import me.roryclaasen.rorysmodcore.asm.MCPNames;
import me.roryclaasen.rorysmodcore.transformer.SleepingTransformer;

@SortingIndex(1001)
@MCVersion(value = "1.7.10")
@DependsOn("forge")
public class RorysModCorePlugin implements IFMLLoadingPlugin {

	@Override
	public String[] getASMTransformerClass() {
		List<String> list = new ArrayList<String>();

		list.add(SleepingTransformer.class.getName());

		String[] sList = new String[list.size()];
		for (int i = 0; i < sList.length; i++) {
			sList[i] = list.get(i);
		}
		return sList;
	}

	@Override
	public String getModContainerClass() {
		return RorysModCore.class.getName();
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		ASMHelper.isMCP = !(Boolean) data.get("runtimeDeobfuscationEnabled");
		MCPNames.use();
	}

	@Override
	public String getAccessTransformerClass() {
		return RorysModAccessTransformer.class.getCanonicalName();
	}
}