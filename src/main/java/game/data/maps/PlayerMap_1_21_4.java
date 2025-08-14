package game.data.maps;

import packets.DataTypeProvider;

public class PlayerMap_1_21_4 extends PlayerMap {
    public PlayerMap_1_21_4(int id) {
        super(id);
    }

    @Override
    public void parse(DataTypeProvider provider) {
        this.scale = provider.readNext();
        this.locked = provider.readBoolean();

        parseIcons(provider);
        parseMapImage(provider);
    }

    private void parseIcons(DataTypeProvider provider) {
        icons.clear();

        boolean hasIcons = provider.readBoolean();
        if (!hasIcons) {
            return;
        }

        int iconCount = provider.readVarInt();
        for (int i = 0; i < iconCount; i++) {
            Icon icon = Icon.of(provider);
            if (icon != null) {
                icons.add(icon);
            }
        }
    }
}