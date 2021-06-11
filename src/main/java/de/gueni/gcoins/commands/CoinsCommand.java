package de.gueni.gcoins.commands;

import de.gueni.coins.user.CoinUser;
import de.gueni.gcoins.GCoinPlugin;
import de.gueni.gcoins.commands.simple.SimpleCommand;
import de.gueni.gcoins.handler.ConfigHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoinsCommand extends SimpleCommand {

    private final GCoinPlugin plugin;
    private final ConfigHandler configHandler;

    public CoinsCommand( String permission, String noPermissionMessage, GCoinPlugin plugin ) {
        super( permission, noPermissionMessage );
        this.plugin = plugin;
        this.configHandler = plugin.getConfigHandler();
        this.plugin.getCommand( "coins" ).setExecutor( this::onCommand );
    }

    @Override
    protected void run( CommandSender commandSender, String[] args ) {
        if ( !( args.length >= 1 ) ) {

            if ( !( commandSender instanceof Player ) ) {
                commandSender.sendMessage( configHandler.getWrongArgs() + "/coins <Player>" );
                return;
            }

            Player player = (Player) commandSender;
            CoinUser user = CoinUser.getUser( player );
            commandSender.sendMessage( String.format( configHandler.getMessage( "commands.coins.sender_message" ), user.getCoins() ) );

        } else {

            if ( !commandSender.hasPermission( configHandler.getMessage( "commands.coins.permission_other" ) ) ) {
                commandSender.sendMessage( configHandler.getNotPermitted() );
                return;
            }

            Player target = Bukkit.getPlayer( args[0] );

            if ( target == null ) {
                commandSender.sendMessage( String.format( configHandler.getPlayerNotOnline(), args[0] ) );
                return;
            }

            CoinUser user = CoinUser.getUser( target );
            commandSender.sendMessage( String.format( configHandler.getMessage( "commands.coins.sender_other_player_message" ), target.getName(), user.getCoins() ) );
        }
    }
}
