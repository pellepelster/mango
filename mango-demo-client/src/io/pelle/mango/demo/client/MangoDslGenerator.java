package io.pelle.mango.demo.client;

public class MangoDslGenerator {

	private StringBuilder sb = new StringBuilder();

	private int indent = 0;

	private boolean newLineStart = false;

	public static MangoDslGenerator create() {
		return new MangoDslGenerator();
	}

	private MangoDslGenerator() {
	}

	private MangoDslGenerator sWithIndention(String string) {

		if (newLineStart) {

			sb.append("<br/>");

			for (int i = 0; i < indent; i++) {
				sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
			}

			newLineStart = false;
		}

		sb.append(string);

		return this;
	}

	private MangoDslGenerator coloredString(String colorString, String color) {
		return sWithIndention("<span style=\"color:" + color + ";font-weight:bold\">" + colorString + "</span>");
	}

	public MangoDslGenerator keyword(String keyword) {
		return coloredString(" " + keyword + " ", "#950055");
	}

	public MangoDslGenerator s(String string) {
		return sWithIndention(" " + string + " ");
	}

	public MangoDslGenerator br() {
		newLineStart = true;
		return this;
	}

	public MangoDslGenerator incrementIndent() {
		br();
		indent++;
		return this;
	}

	public MangoDslGenerator decrementIndent() {
		br();
		indent--;
		return this;
	}

	public MangoDslGenerator block(String keyword, String name, String featureKeyword, String feature) {
		
		keyword(keyword);
		s(name);
		s("{");
		incrementIndent();
		keyword(featureKeyword);
		s(feature);
		decrementIndent();
		s("}");
		br();
		
		return this;
	}
	
	@Override
	public String toString() {
		return sb.toString();
	}

	public MangoDslGenerator abbr() {
		br();
		coloredString("[...]", "#999");
		br();
		return this;
	}

}
