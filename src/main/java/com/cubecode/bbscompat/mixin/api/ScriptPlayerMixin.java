package com.cubecode.bbscompat.mixin.api;

import com.cubecode.api.scripts.code.entities.ScriptPlayer;
import com.mojang.brigadier.arguments.StringArgumentType;
import mchorse.bbs_mod.BBSCommands;
import mchorse.bbs_mod.data.DataToString;
import mchorse.bbs_mod.forms.FormUtils;
import mchorse.bbs_mod.forms.entities.MCEntity;
import mchorse.bbs_mod.forms.forms.Form;
import mchorse.bbs_mod.mixin.PlayerEntityMixin;
import mchorse.bbs_mod.morphing.Morph;
import mchorse.bbs_mod.network.ServerNetwork;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ScriptPlayer.class)
public abstract class ScriptPlayerMixin {
    @Shadow public abstract PlayerEntity getMinecraftPlayer();

    public void setForm(String formData) {
        try {
            Form form = FormUtils.fromData(DataToString.mapFromString(formData));
            ServerNetwork.sendMorphToTracked((ServerPlayerEntity) this.getMinecraftPlayer(), form);
            Morph.getMorph(this.getMinecraftPlayer()).setForm(FormUtils.copy(form));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
