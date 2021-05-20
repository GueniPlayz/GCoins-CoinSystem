package de.gueni.gcoins.commands;

import de.gueni.coins.user.CoinUser;
import de.gueni.gcoins.GCoinsPlugin;
import de.gueni.gcoins.commands.simple.SimpleCommand;
import de.gueni.gcoins.handler.ConfigHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoinsCommand extends SimpleCommand {

    private final GCoinsPlugin plugin;
    private final ConfigHandler configHandler;

    public CoinsCommand( String permission, String noPermissionMessage, GCoinsPlugin plugin ) {
        super( permission, noPermissionMessage );
        this.plugin = plugin;
        this.configHandler = plugin.getConfigHandler();
        this.plugin.getCommand( "coins" ).setExecutor( this::onCommand );
    }

    @Override
    protected void run( CommandSender commandSender, String[] args ) {
        if ( !( args.length >= 1 ) ) {

            if ( !( commandSender instanceof Player ) ) {
                commandSender.sendMessage( configHandler.getPrefix() + ChatColor.DARK_RED + "Sender must be a player!" );
                return;
            }

            CoinUser user = CoinUser.getUser( ( (Player) commandSender ).getPlayer() );
            commandSender.sendMessage( configHandler.getMessage( String.format( "commands.coins.sender_message", user.getCoins() ) ) );

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
