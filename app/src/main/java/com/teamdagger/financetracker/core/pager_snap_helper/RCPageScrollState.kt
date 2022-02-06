package com.teamdagger.financetracker.core.pager_snap_helper

sealed class RCPageScrollState{
    class Idle: RCPageScrollState()
    class Dragging: RCPageScrollState()
    class Settling: RCPageScrollState()
}
