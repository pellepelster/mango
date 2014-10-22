package io.pelle.mango.server.validator;

import io.pelle.mango.client.base.messages.IMessage;
import io.pelle.mango.client.base.messages.Message;
import io.pelle.mango.server.Messages;

public class ValidatorMessages {

	public static final String VALIDATOR_NATURALKEY_MESSAGE = "validator.naturalkey.message";

	public static final String VALIDATOR_NATURALKEY_MESSAGE_HUMAN = "validator.naturalkey.message.human";

	public static final String VALIDATOR_NATURALKEY_MANTATORY_MESSAGE = "validator.naturalkey.mandatory.message";

	public static final String VALIDATOR_NATURALKEY_MANTATORY_MESSAGE_HUMAN = "validator.naturalkey.mandatory.message.human";

	public static final String VALIDATOR_MANDATORY_ATTRIBUTE_MESSAGE = "validator.mandatory.attribute.message";

	public static final String VALIDATOR_MANDATORY_ATTRIBUTE_MESSAGE_HUMAN = "validator.mandatory.attribute.message.human";

	public static final String VALIDATOR_MANDATORY_LIST_MESSAGE = "validator.mandatory.LIST.message";

	public static final String VALIDATOR_MANDATORY_LIST_MESSAGE_HUMAN = "validator.mandatory.LIST.message.human";

	public static final IMessage NATURAL_KEY = new Message(IMessage.SEVERITY.ERROR, "NATURAL_KEY", Messages.getString(VALIDATOR_NATURALKEY_MESSAGE), Messages.getString(VALIDATOR_NATURALKEY_MESSAGE_HUMAN));

	public static final IMessage NATURAL_KEY_MANDATORY = new Message(IMessage.SEVERITY.ERROR, "NATURAL_KEY_MANDATORY", Messages.getString(VALIDATOR_NATURALKEY_MANTATORY_MESSAGE),
			Messages.getString(VALIDATOR_NATURALKEY_MANTATORY_MESSAGE_HUMAN));

	public static final IMessage MANDATORY_ATTRIBUTE = new Message(IMessage.SEVERITY.ERROR, "MANDATORY_ATTRIBUTE", Messages.getString(VALIDATOR_MANDATORY_ATTRIBUTE_MESSAGE), Messages.getString(VALIDATOR_MANDATORY_ATTRIBUTE_MESSAGE_HUMAN));

	public static final IMessage MANDATORY_LIST = new Message(IMessage.SEVERITY.ERROR, "MANDATORY_LIST", Messages.getString(VALIDATOR_MANDATORY_LIST_MESSAGE), Messages.getString(VALIDATOR_MANDATORY_LIST_MESSAGE_HUMAN));

	// max length
	private static final String VALIDATOR_ATTRIBUTE_MAX_LENGTH_MESSAGE = "validator.maxlength.message";

	private static final String VALIDATOR_ATTRIBUTE_MAX_LENGTH_MESSAGE_HUMAN = "validator.maxlength.message.human";

	public static final IMessage STRING_ATTRIBUTE_MAX_LENGTH = new Message(IMessage.SEVERITY.ERROR, "ATTRIBUTE_MAX_LENGTH", Messages.getString(VALIDATOR_ATTRIBUTE_MAX_LENGTH_MESSAGE),
			Messages.getString(VALIDATOR_ATTRIBUTE_MAX_LENGTH_MESSAGE_HUMAN));

	// min length
	private static final String VALIDATOR_ATTRIBUTE_MIN_LENGTH_MESSAGE = "validator.minlength.message";

	private static final String VALIDATOR_ATTRIBUTE_MIN_LENGTH_MESSAGE_HUMAN = "validator.minlength.message.human";

	public static final IMessage STRING_ATTRIBUTE_MIN_LENGTH = new Message(IMessage.SEVERITY.ERROR, "ATTRIBUTE_MIN_LENGTH", Messages.getString(VALIDATOR_ATTRIBUTE_MIN_LENGTH_MESSAGE),
			Messages.getString(VALIDATOR_ATTRIBUTE_MIN_LENGTH_MESSAGE_HUMAN));

}
