package org.sobadfish.bedwar.room.floattext;

import cn.nukkit.level.Position;
import org.sobadfish.bedwar.entity.BedWarFloatText;
import org.sobadfish.bedwar.item.ItemInfo;
import org.sobadfish.bedwar.item.config.MoneyItemInfoConfig;
import org.sobadfish.bedwar.manager.FloatTextManager;
import org.sobadfish.bedwar.player.PlayerInfo;
import org.sobadfish.bedwar.room.GameRoom;
import org.sobadfish.bedwar.world.config.WorldInfoConfig;

public class FloatTextInfo {

    public FloatTextInfoConfig floatTextInfoConfig;

    public BedWarFloatText bedWarFloatText;

    public FloatTextInfo(FloatTextInfoConfig config){
        this.floatTextInfoConfig = config;
    }

    public FloatTextInfo init(GameRoom room){
        try{
            Position position = WorldInfoConfig.getPositionByString(floatTextInfoConfig.position);
            bedWarFloatText = BedWarFloatText.showFloatText(floatTextInfoConfig.name,position,"");
            if(bedWarFloatText != null){
                bedWarFloatText.room = room;
            }

        }catch (Exception e){
            return null;
        }

        return this;
    }

    public boolean stringUpdate(GameRoom room){
        String text = floatTextInfoConfig.text;
        if(room == null){
            return false;
        }
        if(room.getWorldInfo() == null){
            return false;
        }
        for(ItemInfo moneyItemInfoConfig: room.getWorldInfo().getInfos()){
            MoneyItemInfoConfig config = moneyItemInfoConfig.getItemInfoConfig().getMoneyItemInfoConfig();
            text = text
                    .replace("%"+config.getName()+"%",config.getCustomName())
                    .replace("%"+config.getName()+"-time%", PlayerInfo.formatTime1((moneyItemInfoConfig.getResetTick() - moneyItemInfoConfig.getTick()))+"");
        }
        if(bedWarFloatText != null){
            if(bedWarFloatText.isClosed()){
                FloatTextManager.removeFloatText(bedWarFloatText);
                init(room);
            }
            bedWarFloatText.setText(text);
        }
        return true;
    }
}
