package de.mineformers.mcreflect.tests.mod

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import de.mineformers.mcreflect.introspection.RefType
import net.minecraft.client.Minecraft
import net.minecraft.util.Timer

@Mod(modid = "MacroReflectionTest", version = "1.0")
class TestMod {
  @EventHandler
  def init(event: FMLInitializationEvent): Unit = {
    val timer = RefType.of[Minecraft].fields.fullscreen[Boolean]
  }
}