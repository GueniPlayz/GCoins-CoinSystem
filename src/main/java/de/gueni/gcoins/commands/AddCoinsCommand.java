package de.gueni.gcoins.commands;

import de.gueni.coins.user.CoinUser;
import de.gueni.gcoins.GCoinPlugin;
import de.gueni.gcoins.commands.simple.SimpleCommand;
import de.gueni.gcoins.handler.ConfigHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddCoinsCommand extends SimpleCommand {

    private final GCoinPlugin plugin;
    private final ConfigHandler configHandler;

    public AddCoinsCommand( String permission, String noPermissionMessage, GCoinPlugin plugin ) {
        super( permission, noPermissionMessage );
        this.plugin = plugin;
        this.configHandler = plugin.getConfigHandler();
        this.plugin.getCommand( "addcoins" ).setExecutor( this );
    }

    @Override
    protected void run( CommandSender commandSender, String[] args ) {
        if ( args.length == 2 ) {
            Player target = Bukkit.getPlayer( args[0] );

            if ( target == null ) {
                commandSender.sendMessage( String.format( configHandler.getPlayerNotOnline(), args[0] ) );
                return;
            }

            if ( !isDouble( args[1] ) ) {
                commandSender.sendMessage( String.format( configHandler.getNumericInput(), args[1] ) );
                return;
            }

            CoinUser user = CoinUser.getUser( target );
            double coins = Double.parseDouble( args[1] );

            if ( !( coins > 0 ) ) {
                commandSender.sendMessage( String.format( configHandler.getNumericInput(), args[1] ) );
                return;
            }

            user.addCoins( coins );
            target.sendMessage( String.format( configHandler.getMessage( "commands.add_coins.target_message" ), coins ) );
            commandSender.sendMessage( String.format( configHandler.getMessage( "commands.add_coins.sender_message" ), target.getName(), coins ) );

        } else {
            commandSender.sendMessage( configHandler.getWrongArgs() + "/addcoins <Player> <Amount>" );
        }
    }


}
