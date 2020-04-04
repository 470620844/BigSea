package com.chs.lib_core.navigation

import android.content.ComponentName
import androidx.fragment.app.FragmentActivity
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphNavigator
import androidx.navigation.fragment.FragmentNavigator
import com.blankj.utilcode.util.Utils

/**
 * author：chs
 * date：2020/3/29
 * des： 构建一个导航图
 */
class NavGraphBuilder {

    companion object {
        fun build(navController: NavController,activity:FragmentActivity,containerId:Int) {
            val navigatorProvider = navController.navigatorProvider

            val fragmentNavigator = CustomFragmentNavigator(activity,activity.supportFragmentManager,
                containerId)
            navigatorProvider.addNavigator(fragmentNavigator)
//            val fragmentNavigator =
//                navigatorProvider.getNavigator<FragmentNavigator>(FragmentNavigator::class.java)
            val activityNavigator =
                navigatorProvider.getNavigator<ActivityNavigator>(ActivityNavigator::class.java)

            val destinationMap = NavConfig.getDestinationMap()
            val navGraph = NavGraph(NavGraphNavigator(navigatorProvider))

            for ((key,destination) in destinationMap){
                if(destination.isFragment){
                    val fragmentDestination = fragmentNavigator.createDestination()
                    fragmentDestination.className = destination.className!!
                    fragmentDestination.id = destination.id
                    fragmentDestination.addDeepLink(destination.pageUrl!!)
                    navGraph.addDestination(fragmentDestination)
                }else{
                    val activityDestination = activityNavigator.createDestination()
                    activityDestination.id = destination.id
                    activityDestination.setComponentName(ComponentName(Utils.getApp().packageName,
                        destination.className!!))
                    activityDestination.addDeepLink(destination.pageUrl!!)
                    navGraph.addDestination(activityDestination)
                }
                if(destination.asStarter){
                    navGraph.startDestination = destination.id
                }
            }
            navController.graph = navGraph
        }
    }
}