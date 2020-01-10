package com.hao.plugin.adapter

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

public class TestMethodVisitor extends MethodVisitor {

    public TestMethodVisitor(MethodVisitor methodVisitor) {
        super(Opcodes.ASM7, methodVisitor)
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        //方法执行之前打印
        mv.visitLdcInsn(" before method exec");
        mv.visitLdcInsn(" [ASM 测试] method in " + owner + " ,name=" + name);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                "android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(Opcodes.POP)

        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);

        //方法执行之后打印
        mv.visitLdcInsn(" after method exec");
        mv.visitLdcInsn(" method in " + owner + " ,name=" + name);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC,
                "android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);
        mv.visitInsn(Opcodes.POP)
    }
}