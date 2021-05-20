package de.gueni.gcoins.commands.simple;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class SimpleCommand implements CommandExecutor {

    private final String permission, noPermissionMessage;

    public SimpleCommand( String permission, String noPermissionMessage ) {
        this.permission = permission;
        this.noPermissionMessage = noPermissionMessage;
    }

    @Override
    public boolean onCommand( CommandSender commandSender, Command command, String s, String[] args ) {

        if ( !commandSender.hasPermission( permission ) ) {
            commandSender.sendMessage( noPermissionMessage );
            return true;
        }

        run( commandSender, args );
        return false;
    }

    protected abstract void run( CommandSender commandSender, String[] args );

    protected boolean isDouble( String key ) {
        try {
            Double.parseDouble( key );
            return true;
        } catch ( NumberFormatException exception ) {
            return false;
        }
    }
}
