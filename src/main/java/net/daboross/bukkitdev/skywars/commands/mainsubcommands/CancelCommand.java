/*
 * Copyright (C) 2013 Dabo Ross <http://www.daboross.net/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.daboross.bukkitdev.skywars.commands.mainsubcommands;

import net.daboross.bukkitdev.commandexecutorbase.ColorList;
import net.daboross.bukkitdev.commandexecutorbase.SubCommand;
import net.daboross.bukkitdev.commandexecutorbase.filters.ArgumentFilter;
import net.daboross.bukkitdev.skywars.api.SkyWars;
import net.daboross.bukkitdev.skywars.api.game.SkyIDHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Dabo Ross <http://www.daboross.net/>
 */
public class CancelCommand extends SubCommand {

    private final SkyWars plugin;

    public CancelCommand( SkyWars plugin ) {
        super( "cancel", true, "skywars.cancel", "Cancels a current game with the given id" );
        addArgumentNames( "ID" );
        this.addCommandFilter( new ArgumentFilter( ArgumentFilter.ArgumentCondition.LESS_THAN, 2, ColorList.ERR + "Too many arguments!" ) );
        this.addCommandFilter( new ArgumentFilter( ArgumentFilter.ArgumentCondition.GREATER_THAN, 0, ColorList.ERR + "Not enough arguments!" ) );
        this.plugin = plugin;
    }

    @Override
    public void runCommand( CommandSender sender, Command baseCommand, String baseCommandLabel, String subCommandLabel, String[] subCommandArgs ) {
        int id;
        try {
            id = Integer.parseInt( subCommandArgs[0] );
        } catch ( NumberFormatException ex ) {
            sender.sendMessage( ColorList.ERR_ARGS + subCommandArgs[0] + ColorList.ERR + " isn't an integer!" );
            return;
        }
        SkyIDHandler idh = plugin.getIDHandler();
        if ( idh.getGame( id ) == null ) {
            sender.sendMessage( ColorList.ERR + "There aren't any games with the id " + ColorList.ERR_ARGS + id );
            return;
        }
        sender.sendMessage( ColorList.REG + "Canceling game " + ColorList.DATA + id );
        plugin.getGameHandler().endGame( id, true );
    }
}
