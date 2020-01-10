package com.hao.plugin.transform

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.internal.pipeline.TransformManager
import com.hao.plugin.adapter.TestMethodClassAdapter
import org.apache.commons.io.FileUtils
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

class CostTransform extends Transform {

    Project project

    CostTransform(Project project) {
        this.project = project
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        println("===== ASM Transform =====")
        println("${transformInvocation.inputs}")
        println("${transformInvocation.referencedInputs}")
        println("${transformInvocation.outputProvider}")
        println("${transformInvocation.incremental}")

        boolean isIncremental = transformInvocation.isIncremental()
        Collection<TransformInput> inputs = transformInvocation.inputs
        Collection<TransformInput> referencedInputs = transformInvocation.getReferencedInputs()
        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider()

        for (TransformInput input: inputs) {
            for (JarInput jarInput: input.getJarInputs()) {
                File dest = outputProvider.getContentLocation(
                        jarInput.getFile().getAbsolutePath(),
                        jarInput.getContentTypes(),
                        jarInput.getScopes(),
                        Format.JAR
                )
                transformJar(jarInput.getFile(), dest)
            }

            for (DirectoryInput directoryInput: input.getDirectoryInputs()) {
                File dest = outputProvider.getContentLocation(
                        directoryInput.getName(),
                        directoryInput.getContentTypes(),
                        directoryInput.getScopes(),
                        Format.DIRECTORY
                )
                transformDir(directoryInput.getFile(), dest)
            }
        }
    }

    private static void transformJar(File input, File dest) {
        FileUtils.copyFile(input, dest)
    }

    private static void transformDir(File input, File dest) {
        if (dest.exists()) {
            FileUtils.forceDelete(dest)
        }
        FileUtils.forceMkdir(dest)
        String srcDirPath = input.getAbsolutePath()
        String destDirPath = dest.getAbsolutePath()

        println("srcDirPath = " + input.absolutePath)
        println("destDirPath = " + dest.absolutePath)
        if (input.isDirectory()) {
            for (File file: input.listFiles()) {
                transformDir(file, file.replace(srcDirPath, destDirPath))
            }
        } else if (input.isFile()) {
            transformSingleFile(input, input.replace(srcDirPath, destDirPath))
        }

//        for (File file : input.listFiles()) {
//            String destFilePath = file.absolutePath.replace(srcDirPath, destDirPath)
//            File destFile = new File(destFilePath)
//            if (file.isDirectory() && !file.name.equals("META-INF")) {
//                transformDir(file, destFile)
//            } else if (file.isFile()) {
//                FileUtils.touch(destFile)
//                transformSingleFile(file, destFile)
//            }
//        }
    }

    private static void transformSingleFile(File input, File dest) {
        FileUtils.touch(dest)
        weave(input.getAbsolutePath(), dest.getAbsolutePath())
    }

    private static void weave(String inputPath, String outputPath) {
        try {
            FileInputStream is = new FileInputStream(inputPath)
            ClassReader cr = new ClassReader(is)
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES)
            TestMethodClassAdapter adapter = new TestMethodClassAdapter(cw)
            cr.accept(adapter, 0)
            FileOutputStream fos = new FileOutputStream(outputPath)
            fos.write(cw.toByteArray())
            fos.close()
        } catch (IOException e) {
            e.printStackTrace()
        }
    }

    @Override
    String getName() {
        return CostTransform.simpleName
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }
}