
export function handleTradeResponse(data, form) {
    switch (data.responseResult) {
      case 'SUCCESSFUL_BUY':
        return {
          message: `Bought ${form.quantity} of ${form.symbol} successfully at price ${data.price}.
  Funds before the trade: ${data.fundsBeforeTrade}
  Funds after the trade: ${data.fundsAfterTrade}
  New Balance of ${form.symbol}: ${data.newQuantity}`,
          error: '',
        };
        
      case 'SUCCESSFUL_SALE' :
        return {
            message: `Sold ${form.quantity} of ${form.symbol} successfully at price ${data.price}.
        Funds before the trade: ${data.fundsBeforeTrade}
        Funds after the trade: ${data.fundsAfterTrade}
        New Balance of ${form.symbol}: ${data.newQuantity}`,
        error: ''
        };

      case 'SUCCESSFUL_REGISTRATION' :
          return {
              message: `Successfully registeres as ${data.username} with email ${data.email}`,
          error: ''
          };
  

      case 'SUCCESSFUL_RESET' :
            return {
                message: `Successfully reset the data for user ${data.username}`,
            error: ''
            };

      case 'BAD_AUTHENTICATION':
        return {
          message: '',
          error: 'Wrong username and password combination.'
        };
  
      case 'INSUFFICIENT_FUNDS':
        return {
          message: '',
          error: `Not enough funds to buy ${form.quantity} of ${form.symbol.split('/')[0]}`
        };
  
      case 'INVALID_SYMBOL':
        return {
          message: '',
          error: 'Selected currency symbol is not valid.'
        };
  
      case 'PRICE_UNAVAILABLE':
        return {
          message: '',
          error: 'Price is currently unavailable for this currency.'
        };
  
      case 'EMAIL_INVALID':
        return {
          message: '',
          error: 'The email address is not valid.'
        };
  
      case 'PASSWORD_INVALID_CHARACTERS':
        return {
          message: '',
          error: 'Password contains invalid characters.'
        };
  
      case 'PASSWORD_TOO_LONG':
        return {
          message: '',
          error: 'Password is too long.'
        };
  
      case 'PASSWORD_TOO_SHORT':
        return {
          message: '',
          error: 'Password is too short.'
        };
  
      case 'USERNAME_INVALID_CHARACTERS':
        return {
          message: '',
          error: 'Username contains invalid characters.'
        };
  
      case 'USERNAME_TAKEN':
        return {
          message: '',
          error: 'This username is already taken.'
        };
  
      case 'USERNAME_TOO_LONG':
        return {
          message: '',
          error: 'Username is too long.'
        };
  
      case 'USERNAME_TOO_SHORT':
        return {
          message: '',
          error: 'Username is too short.'
        };
      
      case 'EMAIL_TAKEN':
        return {
          message: '',
          error: 'The Email is already taken'
        };
      
      case 'EMAIL_TOO_LONG':
        return {
          message: '',
          error: 'Email is too long.'
        };

      case 'NEGATIVE_AMOUNT':
        return {
          message: '',
          error: 'Quantity should be a positive number'
        };
  
      default:
        return {
          message: '',
          error: 'An unknown error occurred during the trade.'
        };
    }
  }
  