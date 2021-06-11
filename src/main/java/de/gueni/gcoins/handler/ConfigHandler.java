package de.gueni.gcoins.handler;

import de.gueni.gcoins.GCoinPlugin;
import lombok.Getter;
import org.bukkit.ChatColor;

import java.util.Objects;

@Getter
public class ConfigHandler {

    private final GCoinPlugin plugin;

    private String prefix;
    private String notPermitted;
    private String playerNotOnline;
    private String wrongArgs;
    private String numericInput;

    public ConfigHandler( GCoinPlugin plugin ) {
        this.plugin = plugin;

        this.prefix = ChatColor.translateAlternateColorCodes( '&', Objects.requireNonNull( plugin.getConfig().getString( "options.chat.prefix" ) ) );
        this.notPermitted = getMessage( "options.chat.not_permitted" );
        this.playerNotOnline = getMessage( "options.chat.player_not_online" );
        this.wrongArgs = getMessage( "options.chat.wrong_args" );
        this.numericInput = getMessage( "options.chat.numeric_input" );

    }

    public String getMessage( String path ) {
        if ( plugin.getConfig().getString( path ) == null )
            return ChatColor.DARK_GRAY + "| " + ChatColor.GOLD + "System " + ChatColor.DARK_GRAY + "» " + ChatColor.GRAY + "Path: " + ChatColor.YELLOW + path + ChatColor.GRAY + " does not exist!";
        return ChatColor.translateAlternateColorCodes( '&', plugin.getConfig().getString( path ).replace( "%prefix%", prefix ) );
    }
}
