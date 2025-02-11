package com.evoslab.cookielicious.common.core;

import com.teamabnormals.blueprint.core.annotations.ConfigKey;
import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class CookieliciousConfig {

	public static class Common {
		// Cookies
		@ConfigKey("vanilla_cookie")
		public final BooleanValue enableVanillaCookies;
		@ConfigKey("strawberry_cookie")
		public final BooleanValue enableStrawberryCookies;
		@ConfigKey("chocolate_cookie")
		public final BooleanValue enableChocolateCookies;
		@ConfigKey("mint_cookie")
		public final BooleanValue enableMintCookies;
		@ConfigKey("banana_cookie")
		public final BooleanValue enableBananaCookies;
		@ConfigKey("adzuki_cookie")
		public final BooleanValue enableAdzukiCookies;
		@ConfigKey("pumpkin_cookie")
		public final BooleanValue enablePumpkinCookies;
		@ConfigKey("beetroot_cookie")
		public final BooleanValue enableBeetrootCookies;
		
		// Cookie tiles
		@ConfigKey("vanilla_cookie_tiles")
		public final BooleanValue enableVanillaCookieTiles;
		@ConfigKey("strawberry_cookie_tiles")
		public final BooleanValue enableStrawberryCookieTiles;
		@ConfigKey("chocolate_cookie_tiles")
		public final BooleanValue enableChocolateCookieTiles;
		@ConfigKey("honey_cookie_tiles")
		public final BooleanValue enableHoneyCookieTiles;
		@ConfigKey("sweet_berry_cookie_tiles")
		public final BooleanValue enableSweetBerryCookieTiles;
		@ConfigKey("mint_cookie_tiles")
		public final BooleanValue enableMintCookieTiles;
		@ConfigKey("banana_cookie_tiles")
		public final BooleanValue enableBananaCookieTiles;
		@ConfigKey("adzuki_cookie_tiles")
		public final BooleanValue enableAdzukiCookieTiles;
		@ConfigKey("cherry_cookie_tiles")
		public final BooleanValue enableCherryCookieTiles;
		@ConfigKey("mulberry_cookie_tiles")
		public final BooleanValue enableMulberryCookieTiles;
		@ConfigKey("maple_cookie_tiles")
		public final BooleanValue enableMapleCookieTiles;
		@ConfigKey("cookie_tiles")
		public final BooleanValue enableCookieTiles;
		
		Common(ForgeConfigSpec.Builder builder) {
			builder.push("common");
			
			// Cookies
			enableVanillaCookies = builder.define("Whether vanilla cookies are enabled", true);
			enableStrawberryCookies = builder.define("Whether strawberry cookies are enabled", true);
			enableChocolateCookies = builder.define("Whether chocolate cookies are enabled", true);
			enableMintCookies = builder.define("Whether mint cookies are enabled", true);
			enableBananaCookies = builder.define("Whether banana cookies are enabled", true);
			enableAdzukiCookies = builder.define("Whether adzuki cookies are enabled", true);
			enablePumpkinCookies = builder.define("Whether pumpkin cookies are enabled", true);
			enableBeetrootCookies = builder.define("Whether beetroot cookies are enabled", true);

			// Cookie tiles
			enableVanillaCookieTiles = builder.define("Whether vanilla cookie tiles are enabled", true);
			enableStrawberryCookieTiles = builder.define("Whether strawberry cookie tiles are enabled", true);
			enableChocolateCookieTiles = builder.define("Whether chocolate cookie tiles are enabled", true);
			enableHoneyCookieTiles = builder.define("Whether honey cookie tiles are enabled", true);
			enableSweetBerryCookieTiles = builder.define("Whether sweet berry cookie tiles are enabled", true);
			enableMintCookieTiles = builder.define("Whether mint cookie tiles are enabled", true);
			enableBananaCookieTiles = builder.define("Whether banana cookie tiles are enabled", true);
			enableAdzukiCookieTiles = builder.define("Whether adzuki cookie tiles are enabled", true);
			enableCookieTiles = builder.define("Whether cookie tiles are enabled", true);
			enableCherryCookieTiles = builder.define("Whether cherry cookie tiles are enabled", true);
			enableMulberryCookieTiles = builder.define("Whether mulberry cookie tiles are enabled", true);
			enableMapleCookieTiles = builder.define("Whether maple cookie tiles are enabled", true);

			builder.pop();
		}
		
	}
	
	public static final ForgeConfigSpec COMMON_SPEC;
	public static final Common COMMON;
	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = specPair.getRight();
		COMMON = specPair.getLeft();
	}
	
}
