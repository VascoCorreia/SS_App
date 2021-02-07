package com.example.ss_companionapp


class Rune(val name: String, val normalAttackDmg: Int, val heavyAttackDmg: Int, val abilityPower: Int, val attackSpeed: Int, var isEquipped: Boolean, val isCustom: Boolean)
{
    var id: Int = 0
    var img : Image = Image("", ByteArray(DEFAULT_BUFFER_SIZE))
}

class Image(var type: String, var data : ByteArray)