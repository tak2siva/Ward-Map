export default class RomanToDec{
	_roman_to_Int(str1) {
		if(str1 == null) return -1;
		var num = this._char_to_int(str1.charAt(0));
		var pre, curr;

		for(var i = 1; i < str1.length; i++){
			curr = this._char_to_int(str1.charAt(i));
			pre = this._char_to_int(str1.charAt(i-1));
			if(curr <= pre){
			num += curr;
			} else {
			num = num - pre*2 + curr;
			}
		}

		return num;
		}

	_char_to_int(c){
		switch (c){
			case 'I': return 1;
			case 'V': return 5;
			case 'X': return 10;
			case 'L': return 50;
			default: return -1;
		}
	}

	toNumber(romanNumber){
		 return this._roman_to_Int(romanNumber.replace(/ /g,'').toUpperCase());
	}

}