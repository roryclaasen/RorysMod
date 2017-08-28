/*
 * Copyright 2017 Rory Claasen
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.roryclaasen.rorysmod.command;

import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;

public abstract class CommandBase extends net.minecraft.command.CommandBase {

	protected final List<String> aliases;

	public CommandBase(List<String> aliases) {
		this.aliases = aliases;
	}

	public int compareTo(Object o) {
		return super.compareTo((ICommand) o);
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public abstract String getCommandName();

	@Override
	public abstract String getCommandUsage(ICommandSender sender);

	@SuppressWarnings("rawtypes")
	@Override
	public List getCommandAliases() {
		return this.aliases;
	}

	@Override
	public abstract void processCommand(ICommandSender sender, String[] args);
}
