package com.hao.plugin.extension

public class TinkerPatchExtension {
    String oldApk
    String outputFolder
    String newApk

    @Override
    String toString() {
        """| oldApk = ${oldApk}
           | outputFolder = ${outputFolder}
           | newApk = ${newApk}
        """.stripMargin()
    }
}