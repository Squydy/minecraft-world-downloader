package packets.handler.version;

import config.Config;
import game.data.WorldManager;
import game.data.dimension.Dimension;
import game.protocol.Protocol;
import packets.builder.PacketBuilder;
import packets.handler.PacketOperator;
import proxy.ConnectionManager;

import java.util.Map;

import static packets.builder.NetworkType.*;

//idk what im doooinnngggg
public class ClientBoundGamePacketHandler_1_21_4 extends ClientBoundGamePacketHandler_1_20_6 {
    public ClientBoundGamePacketHandler_1_21_4(ConnectionManager connectionManager) {
        super(connectionManager);

        Protocol protocol = Config.versionReporter().getProtocol();

        Map<String, PacketOperator> operators = getOperators();

        //login packets new fields
        operators.put("Login", provider -> {
            PacketBuilder replacement = new PacketBuilder(protocol.clientBound("Login"));

            replacement.copy(provider, INT, BOOL); // playerId, hardcore

            //dimension?? might not need?
            int numDimensions = provider.readVarInt();
            String[] dimensionNames = provider.readStringArray(numDimensions);
            WorldManager.getInstance().getDimensionRegistry().setDimensionNames(dimensionNames);

            replacement.writeVarInt(numDimensions);
            replacement.writeStringArray(dimensionNames);

            replacement.copy(provider, VARINT); // maxPlayers

            //view distance stuff
            int viewDist = provider.readVarInt();
            replacement.writeVarInt(Math.max(viewDist, Config.getExtendedRenderDistance()));

            replacement.copy(provider, VARINT, BOOL, BOOL, BOOL); // simulationDist, reducedDebug, showDeathScreen, limitedCrafting

            //dimension
            String dimensionType = provider.readString();
            String dimensionName = provider.readString();
            Dimension dimension = Dimension.fromString(dimensionName);
            dimension.setType(dimensionType);
            WorldManager.getInstance().setDimension(dimension);
            WorldManager.getInstance().setDimensionType(WorldManager.getInstance().getDimensionRegistry().getDimensionType(dimensionType));

            replacement.writeString(dimensionType);
            replacement.writeString(dimensionName);

            replacement.copy(provider, LONG, BYTE, BYTE, BOOL, BOOL);

            replacement.copyRemainder(provider);

            getConnectionManager().getEncryptionManager().sendImmediately(replacement);
            return false;
        });

        operators.put("Respawn", provider -> {
            String dimensionType = provider.readString();
            Dimension dimension = Dimension.fromString(provider.readString());
            dimension.setType(dimensionType);

            WorldManager.getInstance().setDimension(dimension);
            WorldManager.getInstance().getEntityRegistry().reset();

            return true;
        });
    }
}
