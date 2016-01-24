package net.roryclaasen.asm.rorysmodcore;

import java.util.Map;

import net.roryclaasen.asm.rorysmodcore.transformer.WorldServerTransformer;
import net.roryclaasen.rorysmod.util.Arguments;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;

@MCVersion(value = "1.7.10")
public class RorysModCoreLoading implements IFMLLoadingPlugin {

	@Override
	public String[] getASMTransformerClass() {
		return new String[]{Arguments.isExperiment() ? WorldServerTransformer.class.getName() : ""};
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
	public void injectData(Map<String, Object> data) {}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}