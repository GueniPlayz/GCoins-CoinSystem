package de.gueni.gcoins.commands;

import de.gueni.coins.user.CoinUser;
import de.gueni.gcoins.GCoinPlugin;
import de.gueni.gcoins.commands.simple.SimpleCommand;
import de.gueni.gcoins.handler.ConfigHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand extends SimpleCommand {

    private final GCoinPlugin plugin;
    private final ConfigHandler configHandler;

    public PayCommand( String permission, String noPermissionMessage, GCoinPlugin plugin ) {
        super( permission, noPermissionMessage );
        this.plugin = plugin;
        this.configHandler = plugin.getConfigHandler();
        this.plugin.getCommand( "pay" ).setExecutor( this::onCommand );
    }

    @Override
    protected void run( CommandSender commandSender, String[] args ) {
        if ( !( commandSender instanceof Player ) ) {
            commandSender.sendMessage( configHandler.getPrefix() + ChatColor.DARK_RED + " Sender must be a player!" );
            return;
        }

        if ( args.length != 2 ) {
            commandSender.sendMessage( configHandler.getWrongArgs() + "/pay <Player> <Amount>" );
            return;
        }

        Player player = (Player) commandSender;
        Player target = Bukkit.getPlayer( args[0] );

        if ( target == null ) {
            commandSender.sendMessage( String.format( configHandler.getPlayerNotOnline(), args[0] ) );
            return;
        }

        if ( target.equals( player ) ) {
            player.sendMessage( configHandler.getMessage( "commands.pay.target_is_sender" ) );
            return;
        }

        if ( !isDouble( args[1] ) ) {
            commandSender.sendMessage( String.format( configHandler.getNumericInput(), args[1] ) );
            return;
        }

        CoinUser playerUser = CoinUser.getUser( player );
        CoinUser targetUser = CoinUser.getUser( target );
        double coins = Double.parseDouble( args[1] );

        if ( !( coins > 0 ) ) {
            commandSender.sendMessage( String.format( configHandler.getNumericInput(), args[1] ) );
            return;
        }

        if ( !playerUser.hasEnoughCoins( coins ) ) {
            commandSender.sendMessage( configHandler.getMessage( "commands.pay.sender_not_enough_coins" ) );
            return;
        }

        targetUser.addCoins( coins );
        playerUser.removeCoins( coins );

        target.sendMessage( String.format( configHandler.getMessage( "commands.pay.target_message" ), player.getName(), coins ) );
        commandSender.sendMessage( String.format( configHandler.getMessage( "commands.pay.sender_message" ), coins, target.getName() ) );
    }
}
