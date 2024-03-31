package xland.mcmod.poemlover;

import java.util.Objects;

public enum Platform {
	NEO("neo", "net.neoforged.api.distmarker.Dist"),
	FABRIC("fabric", "net.fabricmc.api.Environment"),
	FORGE("forge", "net.minecraftforge.api.distmarker.Dist"),
	;

	Platform(String refmapName, String clazz) {
		this.embedClass = clazz;
		this.refmapName = refmapName;
	}

	private final String embedClass;
	private final String refmapName;
	private static final Platform THIS;

	public static Platform get() {
		return THIS;
	}

	static {
		Platform candidate = null;
		for (var platform: values()) {
			if (hasClass(platform.embedClass)) {
				candidate = platform;
				break;
			}
		}
		THIS = Objects.requireNonNull(candidate, "Not found");
	}

	private static boolean hasClass(String name) {
		name = name.replace('/', '.');
		try {
			Class.forName(name);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	public String getRefmap() {
		return "refmap-" + refmapName + ".json";
	}
}
