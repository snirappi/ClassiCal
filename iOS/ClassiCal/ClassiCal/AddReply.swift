//
//  AddReply.swift
//  ClassiCal
//
//  Created by ZengJintao on 3/24/15.
//  Copyright (c) 2015 CS 307 Team 8. All rights reserved.
//

import Foundation
import UIKit

class AddReply: UIViewController {
    
    @IBOutlet weak var replyContent: UITextField!
    
    var newReply: Reply?
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        println("add reply")
        if segue.identifier == "DoneReply" {
            println("add reply1")
            let content = replyContent.text
            if (content != nil) {
                println("add reply2")
                if (!content.isEmpty) {
                    newReply = Reply(name: "Tim", content: content, time: "now")
                    println("add new reply in newItem")
                }
            }
        }
    }

    @IBAction func cancel(sender: AnyObject) {
        dismissViewControllerAnimated(true, completion: nil)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

}