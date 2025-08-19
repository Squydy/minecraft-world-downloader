package game.data.maps;

import packets.DataTypeProvider;

public class PlayerMap_1_21_4 extends PlayerMap_1_17{
    public PlayerMap_1_21_4(int id) {
        super(id);
    }

    @Override
    public void parse(DataTypeProvider provider) {
        byte scale = provider.readNext();
        if (scale != 0) {
            this.scale = scale;
        }

        this.locked = provider.readBoolean();


        parseIcons(provider);
        parseMapImage(provider);
    }

    public void parseIcons(DataTypeProvider provider) {
        boolean hasIcons = provider.readBoolean();
        if (!hasIcons) { return; }

        super.parseIcons(provider);
    }
}
