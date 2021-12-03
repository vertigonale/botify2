package com.example.android.botify.menu.sub.audio

import com.example.android.botify.R

fun audioList(/*resources: Resources*/): List<Audio> {
//    val resources = Resources()
    return listOf(
        Audio(
            id = 1,
            audio = R.raw.when_we_were_new_mix2,
            name = "When We were New"
//            name = resources.getString(R.string.audio1_name)
        ),
        Audio(
            id = 2,
            audio = R.raw.winter_path_of_liars,
            name = "Winter Path of Liars"
//            name = resources.getString(R.string.audio2_name)
        ),
        Audio(
            id = 3,
            audio = R.raw.driving_with_lights_off,
            name = "Driving with Lights off"
//            name = resources.getString(R.string.audio3_name)
        ),
        Audio(
            id = 4,
            audio = R.raw.ex_machina,
            name = "EX MACHINA"
//            name = resources.getString(R.string.audio4_name)
        ),
        Audio(
            id = 5,
            audio = R.raw.monkey_mind,
            name = "Monkey Mind"
//            name = resources.getString(R.string.audio5_name)
        ),
        Audio(
            id = 6,
            audio = R.raw.mrs_ancholy,
            name = "Mrs. Ancholy"
//            name = resources.getString(R.string.audio6_name)
        ),
        Audio(
            id = 7,
            audio = R.raw.parachute,
            name = "Parachute"
//            name = resources.getString(R.string.audio7_name)
        )
    )
}
