//
//  CustomTableViewCell.swift
//  ClassiCal
//
//  Created by ZengJintao on 15/3/23.
//  Copyright (c) 2015年 CS 307 Team 8. All rights reserved.
//

import UIKit

class CustomTableViewCell: UITableViewCell {

    var result : Int?
    var indexChose: Int?
    
    @IBAction func downClicked(sender: UIButton) {
        //get result from server
        //self.result = 1
        //self.number.text = "\(self.result)"
        //let path = self.
        //indexChose = path!.row
        
    }
    @IBAction func upClicked(sender: UIButton) {
        //var a = self.number.text.toInt() + 1
        //self.number.text = String(a)
    }
    @IBOutlet weak var number: UILabel!
    @IBOutlet weak var replyContent: UILabel!
    @IBOutlet weak var name: UILabel!
    
    
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
   

}
