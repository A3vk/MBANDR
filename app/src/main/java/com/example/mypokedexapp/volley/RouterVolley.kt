package com.example.mypokedexapp.volley

object RouterVolley {
    const val URL = "https://pokeapi.co/api/v2/"

    private const val BERRY_PREFIX = "${URL}berry"
    object Berries {
        const val BASE = "${BERRY_PREFIX}/"
        const val FIRMNESS = "${BERRY_PREFIX}-firmness/"
        const val FLAVOR = "${BERRY_PREFIX}-flavor/"
    }

    private const val CONTEST_PREFIX = "${URL}contest"
    object Contests {
        const val TYPE = "${CONTEST_PREFIX}-type/"
        const val EFFECT = "${CONTEST_PREFIX}-effect/"
        const val SUPER_EFFECT = "${URL}super-contest-effect/"
    }

    private const val ENCOUNTER_PREFIX = "${URL}encounter"
    object Encounters {
        const val METHOD = "${ENCOUNTER_PREFIX}-method/"
        private const val CONDITION_PREFIX = "${ENCOUNTER_PREFIX}-condition"
        object Condition {
            const val BASE = "${CONDITION_PREFIX}/"
            const val VALUE = "${CONDITION_PREFIX}-value/"
        }
    }

    private const val EVOLUTION_PREFIX = "${URL}evolution"
    object Evolution {
        const val CHAIN = "${EVOLUTION_PREFIX}-chain/"
        const val TRIGGER = "${EVOLUTION_PREFIX}-trigger/"
    }

    object Games {
        const val GENERATION = "${URL}generation/"
        const val POKEDEX = "${URL}pokedex/"
        private const val VERSION_PREFIX = "${URL}version"
        object Version {
            const val BASE = "${VERSION_PREFIX}/"
            const val GROUP = "${VERSION_PREFIX}-group/"
        }
    }

    private const val ITEM_PREFIX = "${URL}item"
    object Items {
        const val BASE = "${ITEM_PREFIX}/"
        const val ATTRIBUTE = "${ITEM_PREFIX}-attribute/"
        const val CATEGORY = "${ITEM_PREFIX}-category/"
        const val FLING_EFFECT = "${ITEM_PREFIX}-fling-effect/"
        const val POCKET = "${ITEM_PREFIX}-pocket/"
    }

    private const val LOCATION_PREFIX = "${URL}location"
    object Locations {
        const val BASE = "${LOCATION_PREFIX}/"
        const val AREA = "${LOCATION_PREFIX}-area/"
        const val PAL_PARK_AREA = "${URL}pal-park-area/"
        const val REGION = "${URL}region/"
    }

    // Machines
    const val MACHINE = "${URL}machine/"

    private const val MOVE_PREFIX = "${URL}move"
    object Moves {
        const val BASE = "${MOVE_PREFIX}/"
        const val AILMENT = "${MOVE_PREFIX}-ailment/"
        const val BATTLE_STYLE = "${MOVE_PREFIX}-battle-style/"
        const val CATEGORY = "${MOVE_PREFIX}-category/"
        const val DAMAGE_CLASS = "${MOVE_PREFIX}-damage-class/"
        const val LEARN_METHOD = "${MOVE_PREFIX}-learn-method"
        const val TARGET = "${MOVE_PREFIX}-target"
    }

    private const val POKEMON_PREFIX = "${URL}pokemon"
    object Pokemon {
        const val ABILITY = "${URL}ability/"
        const val CHARACTERISTIC = "${URL}characteristic/"
        const val EGG_GROUP = "${URL}egg-group/"
        const val GENDER = "${URL}gender/"
        const val GROWTH_RATE = "${URL}growth-rate/"
        const val NATURE = "${URL}nature/"
        const val STAT = "${URL}stat/"
        const val TYPE = "${URL}type/"
        const val POKEATHLON_STAT = "${URL}pokeathlon-stat/"
        const val BASE = "${POKEMON_PREFIX}/"
        const val COLOR = "${POKEMON_PREFIX}-color/"
        const val FORM = "${POKEMON_PREFIX}-form/"
        const val HABITAT = "${POKEMON_PREFIX}-habitat/"
        const val SHAPE = "${POKEMON_PREFIX}-shape/"
        const val SPECIES = "${POKEMON_PREFIX}-species/"
    }

    // Utility
    const val LANGUAGE = "${URL}language/"
}