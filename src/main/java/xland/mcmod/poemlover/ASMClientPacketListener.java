package xland.mcmod.poemlover;

import org.objectweb.asm.tree.*;

import java.util.Objects;
import java.util.function.Consumer;

import static org.objectweb.asm.Opcodes.*;

record ASMClientPacketListener(String[] dict) implements Consumer<ClassNode> {
	@Override
	public void accept(ClassNode c) {
		MethodNode m = c.methods.stream().filter(m0 -> {
			CND cnd = d(3);
			return Objects.equals(m0.desc, cnd.desc()) && Objects.equals(m0.name, cnd.name());
		}).findAny().orElseThrow();
		for (AbstractInsnNode instruction : m.instructions) {
			if (instruction instanceof MethodInsnNode method && fitMethod(0, method)) {
				InsnList l = new InsnList(); {
					LabelNode L1 = new LabelNode();	// start
					LabelNode L2 = new LabelNode();	// branch 1
					LabelNode L3 = new LabelNode();	// branch 2
					LabelNode L4 = new LabelNode();	// end

					l.add(L1);
					l.add(new VarInsnNode(ALOAD, 1));	// var `packet`
					l.add(new MethodInsnNode(INVOKEVIRTUAL, d(1).owner(), d(1).name(), d(1).desc()));
					l.add(new FieldInsnNode(GETSTATIC, d(2).owner(), d(2).name(), d(2).desc()));
					l.add(new JumpInsnNode(IF_ACMPNE, L3));	// if not matched, skip
					l.add(L2);
					l.add(new InsnNode(POP));	// discard old value
					l.add(new LdcInsnNode(1.14514F));	// in [0.5, 1.5)
					l.add(new JumpInsnNode(GOTO, L4));
					l.add(L3);
					l.add(L4);

					l.add(new LineNumberNode(10000, L1));
					l.add(new LineNumberNode(10001, L2));
					l.add(new LineNumberNode(10002, L3));
					l.add(new LineNumberNode(10003, L4));
				}
				m.instructions.insert(instruction, l);
			}
		}
	}

	CND d(int idx) {
		return CND.fromDesc(dict[idx]);
	}

	boolean fitMethod(int idx, MethodInsnNode node) {
		CND d = d(idx);
		return Objects.equals(node.owner, d.owner()) &&
				Objects.equals(node.name, d.name()) &&
				Objects.equals(node.desc, d.desc());
	}
}
