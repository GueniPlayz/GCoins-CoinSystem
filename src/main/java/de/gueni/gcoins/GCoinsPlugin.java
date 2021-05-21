package de.gueni.gcoins;

import de.gueni.gcoins.commands.*;
import de.gueni.gcoins.handler.ConfigHandler;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

@Getter
public class GCoinsPlugin extends JavaPlugin {

    private ConfigHandler configHandler;

    @Override
    public void onEnable() {
        setup();
    }

    private void setup() {
        // Checking if CoinAPI is not enabled, if yes we disable the plugin
        if ( !getServer().getPluginManager().getPlugin( "CoinAPI" ).isEnabled() ) {
            getLogger().log( Level.WARNING, "§cGCoins requires CoinAPI to work.. Disabling plugin!" );
            getServer().getPluginManager().disablePlugin( this );
            return;
        }

        // Saving default config and initializing configHandler
        saveDefaultConfig();
        configHandler = new ConfigHandler( this );

        // Registering commands
        new AddCoinsCommand( getConfigHandler().getMessage( "commands.add_coins.permission" ), getConfigHandler().getNotPermitted(), this );
        new RemoveCoinsCommand( getConfigHandler().getMessage( "commands.remove_coins_permission" ), getConfigHandler().getNotPermitted(), this );
        new SetCoinsCommand( getConfigHandler().getMessage( "commands.set_coins_permission" ), getConfigHandler().getNotPermitted(), this );
        new CoinsCommand( getConfigHandler().getMessage( "commands.coins.permission" ), getConfigHandler().getNotPermitted(), this );
        new PayCommand( getConfigHandler().getMessage( "commands.pay_permission" ), getConfigHandler().getNotPermitted(), this );

        getLogger().log( Level.FINE, "§aPlugin started successfully!" );
    }
}
