package org.example.entities.users.member

data class HelpInfo(
    val helpAskedByOtherMembers: MutableList<String> = mutableListOf(),
    val helpRequested: MutableList<String> = mutableListOf()
)
