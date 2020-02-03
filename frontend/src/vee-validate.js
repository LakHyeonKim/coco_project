import {
	extend,
	configure
} from "vee-validate";
import {
	required,
	alpha,
	email,
	confirmed,
	min
} from "vee-validate/dist/rules";
import {
	i18n
} from "./i18n";

configure({
	defaultMessage: (field, values) => {
		// override the field name.
		values._field_ = i18n.t(`${field}`);

		return i18n.t(`validation.${values._rule_}`, values);
	}
});

// Install required rule and message.
extend("required", required);

// Install alpha rule and message.
extend("alpha", alpha);

// Install email rule and message.
extend("email", email);

// Install confirmed rule and message.
extend("confirmed", confirmed);

// Install min rule and message.
extend("min", min);