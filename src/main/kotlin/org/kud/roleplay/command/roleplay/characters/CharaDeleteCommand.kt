package org.kud.roleplay.command.roleplay.characters

import org.kud.roleplay.LOGGER
import org.kud.roleplay.command.meta.CommandContext
import org.kud.roleplay.command.meta.command.Command
import java.sql.SQLException

class CharaDeleteCommand : Command() {

    override fun onInvoke(context: CommandContext) {
        val guildId = context.event.guild.idLong
        val userId = context.event.author.idLong

        if (context.tokenizer.hasMore) {
            val name = context.tokenizer.tail()

            if (context.bot.database.existsRoleplayCharacter(guildId, userId, name)) {
                try {
                    context.bot.database.deleteRoleplayCharacter(guildId, userId, name)
                    context.reply("your character \"$name\" has been deleted!")
                } catch (e: SQLException) {
                    LOGGER.error("Could not delete character.", e)
                    context.replyFail("an error occurred while deleting your character.")
                }
            } else {
                context.replyFail("you have no character with that name.")
            }
        } else {
            context.replyFail("you haven't specified a character name.")
        }
    }

}