package game.data.container;

import game.data.registries.RegistryManager;
import se.llbit.nbt.ByteTag;
import se.llbit.nbt.CompoundTag;
import se.llbit.nbt.IntTag;
import se.llbit.nbt.SpecificTag;
import se.llbit.nbt.StringTag;

public class Slot {
    private int itemId;
    private int count;
    private SpecificTag nbt;

    public Slot(int itemId, byte count, SpecificTag nbt) {
        this.itemId = itemId;
        this.count = count;
        this.nbt = nbt;
    }

    public Slot(String itemName, byte count) {
        this(RegistryManager.getInstance().getItemRegistry().getItemId(itemName), count, null);
    }

    @Override
    public String toString() {
        return "Slot{" +
            "itemId=" + itemId +
            ", Name=" + RegistryManager.getInstance().getItemRegistry().getItemName(itemId) +
            ", count=" + count +
            ", nbt=" + nbt +
            '}';
    }

public CompoundTag toNbt() {
    CompoundTag tag = new CompoundTag();
    String itemName = RegistryManager.getInstance().getItemRegistry().getItemName(itemId);

    tag.add("id", new StringTag(itemName));
    tag.add("Count", new ByteTag(count));

    if (nbt instanceof CompoundTag compound) {
        tag.add("tag", compound);
    }

    // Special case: maps in 1.20.6+
    if ("minecraft:filled_map".equals(itemName)) {
        CompoundTag mapTag = (nbt instanceof CompoundTag comp) ? comp : new CompoundTag();

        // Ensure we have a "map" field
        if (mapTag.get("map") == null) {
            // TODO this should come from MapItemData packet just using static number for testing
            mapTag.add("map", new IntTag(10674));
        }

        tag.add("tag", mapTag);
    }

    return tag;
}


}
