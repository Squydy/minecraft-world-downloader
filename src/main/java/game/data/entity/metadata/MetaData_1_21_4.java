package game.data.entity.metadata;

import packets.DataTypeProvider;
import se.llbit.nbt.ByteTag;
import se.llbit.nbt.CompoundTag;
import se.llbit.nbt.IntTag;
import se.llbit.nbt.StringTag;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Metadata for 1.21.4 entities
 */
public class MetaData_1_21_4 extends MetaData_1_19_3 {

    private static Map<Integer, Consumer<DataTypeProvider>> typeHandlers;

    static {
        typeHandlers = new HashMap<>();
        typeHandlers.put(0, DataTypeProvider::readNext);
        typeHandlers.put(1, DataTypeProvider::readVarInt);
        typeHandlers.put(2, DataTypeProvider::readVarLong);
        typeHandlers.put(3, DataTypeProvider::readFloat);
        typeHandlers.put(4, DataTypeProvider::readString);
        typeHandlers.put(5, DataTypeProvider::readChat);
        typeHandlers.put(6, DataTypeProvider::readOptChat);
        typeHandlers.put(7, DataTypeProvider::readSlot);      // held items
        typeHandlers.put(8, DataTypeProvider::readBoolean);
        typeHandlers.put(9, DataTypeProvider::readBoolean);
        typeHandlers.put(10, DataTypeProvider::readLong);
        typeHandlers.put(11, provider -> {
            if (provider.readBoolean()) {
                provider.readLong();
            }
        });
        typeHandlers.put(12, DataTypeProvider::readVarInt);
        typeHandlers.put(14, DataTypeProvider::readVarInt);
        typeHandlers.put(15, DataTypeProvider::readNbtTag);
        typeHandlers.put(17, (provider -> {
            provider.readVarInt();
            provider.readVarInt();
            provider.readVarInt();
        }));
        typeHandlers.put(18, DataTypeProvider::readOptVarInt);
        typeHandlers.put(19, DataTypeProvider::readVarInt);
    }

    @Override
    public Consumer<DataTypeProvider> getTypeHandler(int i) {
        return typeHandlers.getOrDefault(i, super.getTypeHandler(i));
    }

    // Optional: Index handlers for special entities (item frames, armor stands, etc.)
    @Override
    public Consumer<DataTypeProvider> getIndexHandler(int i) {
        // For ItemFrame
        switch (i) {
            case 7: return DataTypeProvider::readSlot; // will be handled by entity-specific metadata
        }
        return super.getIndexHandler(i);
    }

    @Override
    public void addNbtTags(CompoundTag nbt) {
        super.addNbtTags(nbt);
        // additional fields can be added per entity type if needed
    }
}
