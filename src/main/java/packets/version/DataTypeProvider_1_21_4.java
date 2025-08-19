package packets.version;

import game.data.container.Slot;

public class DataTypeProvider_1_21_4 extends DataTypeProvider_1_20_2 {
    public DataTypeProvider_1_21_4(byte[] finalFullPacket) {
        super(finalFullPacket);
    }

    @Override
public Slot readSlot() {
    // In 1.20.6+, slot encoding:
    // 1. Item count (VarInt)
    int count = readVarInt();
    if (count <= 0) return null;

    // 2. Item name (string, namespaced ID like "minecraft:filled_map")
    String itemName = readString();

    // 3. Item components (NBT-like compound but flattened)
    SpecificTag components = readNbtTag(); // fallback, later you can parse proper "components"

    // Return slot
    return new Slot(
        itemName,
        (byte) count,
        components
    );
}


}
