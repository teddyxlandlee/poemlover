package xland.mcmod.poemlover;

record CND(String owner, String name, String desc) {
	static CND fromDesc(String s) {
		int colon = s.lastIndexOf(':'), dot = s.lastIndexOf('.');
		if (colon < dot) throw new IllegalArgumentException(s);
		return new CND(
			s.substring(0, dot).replace('.', '/'),
			s.substring(dot+1, colon),
			s.substring(colon+1)
		);
	}

	@Override
	public String toString() {
		return owner.replace('/', '.') + '.' + name + ':' + desc;
	}
}
