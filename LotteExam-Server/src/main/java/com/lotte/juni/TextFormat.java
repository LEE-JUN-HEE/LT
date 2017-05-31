package com.lotte.juni;

import com.lotte.juni.clss.Message;

//@PropertySource("text.properties")
public class TextFormat {
	public enum Type {
		hello, find, recommend, notfound, blah,
	}

	public static class Woman {
		//@Value("${woman.young.hello}")
		static public String youngHello = "<div id = 'bottext'>%d세의 젊은 아가씨가 오셨네~~ 난 챗봇입니당~ 궁금한거 물어보긔~</div>";
		//@Value("${woman.young.find}")
		static public String youngFind ="<div id = 'bottext'>%s에 %s 있다냥! 가격은 %d고 주소는 <a href = %s target='_blank'>%s</a> 요기요기!긔긔!</div>";
		static public String youngRecommed = "<div id = 'bottext'>%d 나이대 정도에는 %s를 가장 많이 %s 주소는 <a href = %s target='_blank'>%s</a></div>";
		
		//@Value("${woman.middle.hello}")
		static public String middleHello = "<div id = 'bottext'>%d세의 젊은 언니잖아! 오호홍 난 챗봇이얌 깔깔~ 궁금한거 물어보고 그래~~</div>";
		//@Value("${woman.middle.find}")"
		static public String middleFind = "<div id = 'bottext'>%s에 %s 있네요 호홍! 가격은 %d고 주소는 <a href = %s target='_blank'>%s</a>";
		static public String middleRecommed = "<div id = 'bottext'>%d 나이대 정도에는 %s를 가장 많이 %s 주소는 <a href = %s target='_blank'>%s</a></div>";
		//@Value("${woman.elder.hello}")
		static public String elderHello="<div id = 'bottext'>%d세 어르신. 전 챗봇입니다. 필요한것이 있으면 저에게 말씀해주시면됩니다</div>";
		//@Value("${woman.elder.find}")
		static public String elderFind="<div id = 'bottext'>%s에 %s 있습니다! 가격은 %d고 주소는 <a href = %s target='_blank'>%s</a> 입니다. 멋진 쇼핑 되시길 바랍니다!</div>";
		static public String elderRecommed = "<div id = 'bottext'>%d 나이대 정도에는 %s를 가장 많이 %s 주소는 <a href = %s target='_blank'>%s</a></div>";
		
		static public String recoomendEmpty =  "<div id = 'bottext'>%d 나이대 %s는 아직 데이터가 부족합니다</div>";
	}

	public static class Man {
		//@Value("${man.young.hello}")
		static public String youngHello="<div id = 'bottext'>%d세 행님 오셨슴까~~ 난 챗봇! 추천즐찾 별ㅍ.. 아니ㅋㅋ 즐거운 쇼핑!! 궁금한거 물어보삼</div>";
		//@Value("${man.young.find}")
		static public String youngFind ="<div id = 'bottext'>%s에 %s 있엌ㅋㅋ! 가격은 %d고 주소는 <a href = %s target='_blank'>%s</a> 구매각 오지고?</div>";
		static public String youngRecommed = "<div id = 'bottext'>%d 나이대 정도에는 %s를 가장 많이 %s 주소는 <a href = %s target='_blank'>%s</a></div>";
		//@Value("${man.middle.hello}")
		static public String middleHello = "<div id = 'bottext'>%d세 신사분이 오셨군요! 역시 신세대는 인터넷 쇼핑아닙니까~ 와이프한테 안걸리도록.. 전 챗봇이니 궁금한거 물어보십쇼!</div>";
		//@Value("${man.middle.find}")
		static public String middleFind = "<div id = 'bottext'>%s에 %s 있네요! 가격은 %d고 주소는 <a href = %s target='_blank'>%s</a> 입니다. 중년의 구매력은 대단하죠</div>";
		static public String middleRecommed = "<div id = 'bottext'>%d 나이대 정도에는 %s를 가장 많이 %s 주소는 <a href = %s target='_blank'>%s</a></div>";
		//@Value("${man.elder.hello}")
		static public String elderHello ="<div id = 'bottext'>%d세 어르신. 전 챗봇입니다. 필요한것이 있으면 저에게 말씀해주시면됩니다</div>";
		//@Value("${man.elder.find}")
		static public String elderFind ="<div id = 'bottext'>%s에 %s 있습니다! 가격은 %d고 주소는 <a href = %s target='_blank'>%s</a> 입니다. 멋진 쇼핑 되시길 바랍니다!</div>";
		static public String elderRecommed = "<div id = 'bottext'>%d 나이대 정도에는 %s를 가장 많이 %s 주소는 <a href = %s target='_blank'>%s</a></div>";
		
		static public String recoomendEmpty =  "<div id = 'bottext'>%d 나이대 %s는 아직 데이터가 부족합니다</div>";
	}
	
	static public String emptyMsg = "<div id = 'bottext'>제가 능력이 부족해서 못찾아냈습니다 ㅠㅠ [보낸글 : %s]</div>";
	static public String blah = "<div id = 'bottext'>도움 요청 안하면 말 따라하기만 할거지롱~ %s</div>";

	static public String GetStringformat(Message msg, Type type) {

		if (msg.getGender() % 2 == 0) {
			// 남자
			switch (type) {
			case hello:
				switch (msg.getAge() / 30) {
				case 0:// young
					return Woman.youngHello;
				case 1:// middle
					return Woman.middleHello;
				default :
				case 2:// elder
					return Woman.elderHello;
				}
			case blah:
				break;
			case find:
				switch (msg.getAge() / 30) {
				case 0:// young
					return Woman.youngFind;
				case 1:// middle
					return Woman.middleFind;
				default :
				case 2:// elder
					return Woman.elderHello;
				}
			case notfound:
				break;
			case recommend:
				switch (msg.getAge() / 30) {
				case 0:// young
					return Woman.youngRecommed;
				case 1:// middle
					return Woman.middleRecommed;
				default :
				case 2:// elder
					return Woman.elderRecommed;
				}
			default:
				break;
			}
		} else {
			switch (type) {
			case hello:
				switch (msg.getAge() / 30) {
				case 0:// young
					return Man.youngHello;
				case 1:// middle
					return Man.middleHello;
				default :
				case 2:// elder
					return Man.elderHello;
				}
			case blah:
				break;
			case find:
				switch (msg.getAge() / 30) {
				case 0:// young
					return Man.youngFind;
				case 1:// middle
					return Man.middleFind;
				default :
				case 2:// elder
					return Man.elderHello;
				}

			case notfound:
				break;
			case recommend:
				switch (msg.getAge() / 30) {
				case 0:// young
					return Woman.youngRecommed;
				case 1:// middle
					return Woman.middleRecommed;
				default :
				case 2:// elder
					return Woman.elderRecommed;
				}
			default:
				break;
			}
		}

		return null;
	}
}
